package inputvalidation;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputErrorChecking {
    private Context context;

    public InputErrorChecking(Context context){
        this.context = context;
    }

    /**
     *
     *
     *
     */
    public boolean isTextBoxFilled(TextInputLayout textLayout, EditText textField, String message){
        if (textField.getText().length() == 0){
            textLayout.setError(message);
            hideKeyboardFrom(textField);
            return false;
        }
        else {
            textLayout.setErrorEnabled(false);
        }
            return true;
    }
    /******************************************
     *
     *
     *****************************************/
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
    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
