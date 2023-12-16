/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.pt.devicemanager.scanner;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

import com.pt.devicemanager.R;

/**
 * ScannerFragment class scan required BLE devices and shows them in a list. This class scans and filter
 * devices with standard BLE Service UUID and devices with custom BLE Service UUID. It contains a
 * list and a button to scan/cancel. There is a interface {@link OnDeviceSelectedListener} which is
 * implemented by activity in order to receive selected device. The scanning will continue to scan
 * for 5 seconds and then stop.
 */
public class ScannerFragment extends DialogFragment {
	private final static String TAG = "ScannerFragment";

	private final static String PARAM_UUID = "param_uuid";
	private final static long SCAN_DURATION = 5000;

	private final static int REQUEST_PERMISSION_REQ_CODE = 34; // any 8-bit number

	private BluetoothAdapter mBluetoothAdapter;
	private OnDeviceSelectedListener mListener;
	private DeviceListAdapter mAdapter;
	private final Handler mHandler = new Handler();
	private Button mScanButton;

	private TextView mPermissionRationale;

	private ParcelUuid mUuid;

	private boolean mIsScanning = false;

	public static ScannerFragment getInstance(final UUID uuid) {
		final ScannerFragment fragment = new ScannerFragment();

		final Bundle args = new Bundle();
		if (uuid != null)
			args.putParcelable(PARAM_UUID, new ParcelUuid(uuid));
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Interface required to be implemented by activity.
	 */
	public interface OnDeviceSelectedListener {
		/**
		 * Fired when user selected the device.
		 * 
		 * @param device
		 *            the device to connect to
		 * @param name
		 *            the device name. Unfortunately on some devices {@link BluetoothDevice#getName()}
		 *            always returns <code>null</code>, i.e. Sony Xperia Z1 (C6903) with Android 4.3.
		 *            The name has to be parsed manually form the Advertisement packet.
		 */
		void onDeviceSelected(final BluetoothDevice device, final String name);

		/**
		 * Fired when scanner dialog has been cancelled without selecting a device.
		 */
		void onDialogCanceled();
	}

	/**
	 * This will make sure that {@link OnDeviceSelectedListener} interface is implemented by activity.
	 */
	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		try {
			this.mListener = (OnDeviceSelectedListener) context;
		} catch (final ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement OnUSBDeviceSelectedListener");
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Bundle args = getArguments();
		if (args != null && args.containsKey(PARAM_UUID)) {
			mUuid = args.getParcelable(PARAM_UUID);
		}

		final BluetoothManager manager = (BluetoothManager) requireContext().getSystemService(Context.BLUETOOTH_SERVICE);
		if (manager != null) {
			mBluetoothAdapter = manager.getAdapter();
		}
	}

	@Override
	public void onDestroyView() {
		stopScan();
		super.onDestroyView();
	}

	@NonNull
    @Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
		final View dialogView = getLayoutInflater().inflate(R.layout.fragment_device_selection, null);
		final ListView listview = dialogView.findViewById(android.R.id.list);

		listview.setEmptyView(dialogView.findViewById(android.R.id.empty));
		listview.setAdapter(mAdapter = new DeviceListAdapter(getActivity()));

		builder.setTitle(R.string.scanner_title);
		final AlertDialog dialog = builder.setView(dialogView).create();
		listview.setOnItemClickListener((parent, view, position, id) -> {
			stopScan();
			dialog.dismiss();
			final ExtendedBluetoothDevice d = (ExtendedBluetoothDevice) mAdapter.getItem(position);
			mListener.onDeviceSelected(d.device, d.name);
		});

		mPermissionRationale = (TextView) dialogView.findViewById(R.id.permission_rationale); // this is not null only on API23+

		mScanButton = dialogView.findViewById(R.id.action_cancel);
		mScanButton.setOnClickListener(v -> {
			if (v.getId() == R.id.action_cancel) {
				if (mIsScanning) {
					dialog.cancel();
				} else {
					startScan();
				}
			}
		});

		// PT - No need
		//addBoundDevices();
		if (savedInstanceState == null)
			startScan();
		return dialog;
	}

	@Override
	public void onCancel(@NonNull DialogInterface dialog) {
		super.onCancel(dialog);

		mListener.onDialogCanceled();
	}

	// Dedicated launcher for Bkg location permission handling
	private final ActivityResultLauncher<String> bkgLocPermissionLauncher =
			registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
				if (isGranted) {
					startScan();
				} else {
					mPermissionRationale.setVisibility(View.VISIBLE);
					mScanButton.setEnabled(false);
				}
			});


	ActivityResultCallback<Map<String, Boolean>> cb = new ActivityResultCallback<Map<String, Boolean>>() {
		@Override
		public void onActivityResult(Map<String, Boolean> results) {
			Log.i(TAG, "SF: Permission result for "+ results.toString());

			Boolean fineLocResult = results.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ;
			Boolean scanLocResult = results.getOrDefault(Manifest.permission.BLUETOOTH_SCAN, false) ;
			Boolean postNotiResult = results.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false);


			if (fineLocResult != null && !fineLocResult) {
				Log.e(TAG, "A: PDF: ACCESS_FINE_LOCATION permissions is missing!.");
				mPermissionRationale.setVisibility(View.VISIBLE);
				mScanButton.setEnabled(false);
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
				if (scanLocResult != null && !scanLocResult) {
					Log.e(TAG, "A: PDF: BLUETOOTH_SCAN permissions is missing!.");
					mPermissionRationale.setVisibility(View.VISIBLE);
					mScanButton.setEnabled(false);
				}
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				if (postNotiResult != null && !postNotiResult) {
					Log.e(TAG, "A: PDF: POST_NOTIFICATIONS permissions is missing!.");
					mPermissionRationale.setVisibility(View.VISIBLE);
					mScanButton.setEnabled(false);
				}
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
				// All required permission were granted
				if ((fineLocResult != null && fineLocResult) &&
						(scanLocResult != null && scanLocResult) &&
						(postNotiResult != null && postNotiResult)) {
					// ACCESS BACKGROUND must be asked individually only after ACCESS_FINE_LOCATION is granted
					if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
						Log.i(TAG, "A: PDF: requesting ACCESS_BACKGROUND_LOCATION ...");
						bkgLocPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
					}
				} else {
					Log.e(TAG, "A: PDF: Some or all permissions are missing!.");
					mPermissionRationale.setVisibility(View.VISIBLE);
					mScanButton.setEnabled(false);
				}
			}

		}
	};

	ActivityResultContract<String[],Map<String, Boolean>> contract = new ActivityResultContracts.RequestMultiplePermissions();
	private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(contract, cb);

	/**
	 * Scan for 3 seconds and then stop scanning when a BluetoothLE device is found then mLEScanCallback
	 * is activated This will perform regular scan for custom BLE Service UUID and then filter out.
	 * using class ScannerServiceParser
	 */
	private void startScan() {

		ArrayList<String> permissions = new ArrayList<String>();

		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.BLUETOOTH_SCAN);
				permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
			}
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.POST_NOTIFICATIONS);
			}
		}

		if (!permissions.isEmpty()) {
			Log.i(TAG, "SF: Get permissions "+permissions.toString());
			permissionLauncher.launch(permissions.toArray(new String[permissions.size()]));
			return;
		}


		// Hide the rationale message, we don't need it anymore.
		if (mPermissionRationale != null)
			mPermissionRationale.setVisibility(View.GONE);

		mAdapter.clearDevices();
		mScanButton.setText(R.string.scanner_action_cancel);

		final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
		final ScanSettings settings = new ScanSettings.Builder()
				.setLegacy(false)
				.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
				.setReportDelay(3000)
				.setUseHardwareBatchingIfSupported(false)
				.build();
		final List<ScanFilter> filters = new ArrayList<>();
		filters.add(new ScanFilter.Builder().setServiceUuid(mUuid).build());
		scanner.startScan(filters, settings, scanCallback);

		mIsScanning = true;
		mHandler.postDelayed(() -> {
			if (mIsScanning) {
				stopScan();
			}
		}, SCAN_DURATION);
	}

	/**
	 * Stop scan if user tap Cancel button
	 */
	private void stopScan() {
		if (mIsScanning) {
			mScanButton.setText(R.string.scanner_action_scan);

			final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
			scanner.stopScan(scanCallback);

			mIsScanning = false;
		}
	}

	private ScanCallback scanCallback = new ScanCallback() {
		@Override
		public void onScanResult(final int callbackType, final ScanResult result) {
			// do nothing
		}

		@Override
		public void onBatchScanResults(final List<ScanResult> results) {
			mAdapter.update(results);
		}

		@Override
		public void onScanFailed(final int errorCode) {
			// should never be called
		}
	};

//	private void addBoundDevices() {
//		final Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
//		mAdapter.addBondedDevices(devices);
//	}
}
