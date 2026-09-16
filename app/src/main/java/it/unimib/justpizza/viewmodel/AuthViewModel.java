package it.unimib.justpizza.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import it.unimib.justpizza.repository.AuthRepository;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository = new AuthRepository();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<FirebaseUser> getUser() { return user; }

    public boolean isLoggedIn() {
        return repository.isLoggedIn();
    }

    public void login(String email, String password) {
        loading.setValue(true);
        error.setValue(null);
        repository.login(email, password, task -> {
            loading.setValue(false);
            if (task.isSuccessful()) {
                user.setValue(repository.getCurrentUser());
            } else {
                String msg = task.getException() != null
                        ? task.getException().getMessage()
                        : "Login fallito";
                error.setValue(msg);
            }
        });
    }

    public void register(String email, String password) {
        loading.setValue(true);
        error.setValue(null);
        repository.register(email, password, task -> {
            loading.setValue(false);
            if (task.isSuccessful()) {
                user.setValue(repository.getCurrentUser());
            } else {
                String msg = task.getException() != null
                        ? task.getException().getMessage()
                        : "Registrazione fallita";
                error.setValue(msg);
            }
        });
    }

    public void logout() {
        repository.logout();
        user.setValue(null);
    }
}