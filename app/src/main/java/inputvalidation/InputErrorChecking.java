package inputvalidation;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.widget.EditText;

public class InputErrorChecking {
    private Context context;
    public InputErrorChecking(Context context){
        this.context = context;
    }

    public boolean isTextBoxFilled(TextInputLayout textLayout, EditText textField, String message){
        if (textField.getText().length() == 0){
            textLayout.setError(message);
            return false;
        }
        else {
            textLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean doesConfirmationMatch(TextInputLayout passwordLayout, EditText password, EditText passwordConfirmation, String errMessage){
        String pw = password.getText().toString().trim();
        String pwConfirm = passwordConfirmation.getText().toString().trim();
        if (pw.contentEquals(pwConfirm)) {
            passwordLayout.setErrorEnabled(false);
            return true;
        }
        else {
            passwordLayout.setError(errMessage);
            return false;
        }
    }
    public boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean isPasswordValid(TextInputLayout passwordLayout, EditText password, String[] errMsgArray){
        if (password.getText().length() < 6){
            passwordLayout.setError(errMsgArray[0]);
            return false;
        }
        else if (!password.getText().toString().trim().matches(".*[0-9]+")){
            passwordLayout.setError(errMsgArray[1]);
            return false;
        }
        return true;
    }
}
