package iot.app.smarthome.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import iot.app.smarthome.databinding.FragmentMineBinding;
import iot.app.smarthome.vm.mine.MineViewModel;

public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private MineViewModel mineViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mineViewModel = new MineViewModel();
        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.setMineViewModel(mineViewModel);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
