package it.unimib.justpizza.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import it.unimib.justpizza.R;
import it.unimib.justpizza.WelcomeActivity;
import it.unimib.justpizza.repository.AuthRepository;

public class ProfileFragment extends Fragment {

    private final AuthRepository authRepository = new AuthRepository();

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvEmail = view.findViewById(R.id.tv_profile_email);
        MaterialButton btnLogout = view.findViewById(R.id.btn_logout);

        String email = authRepository.getEmail();
        tvEmail.setText(email.isEmpty() ? "Non connesso" : email);

        btnLogout.setOnClickListener(v -> {
            authRepository.logout();
            Intent intent = new Intent(requireContext(), WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}