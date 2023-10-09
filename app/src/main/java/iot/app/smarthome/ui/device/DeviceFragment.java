package iot.app.smarthome.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import iot.app.smarthome.databinding.FragmentDeviceBinding;
import iot.app.smarthome.vm.device.DeviceViewModel;

public class DeviceFragment extends Fragment {
    private FragmentDeviceBinding binding;
    private DeviceViewModel DeviceViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DeviceViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
