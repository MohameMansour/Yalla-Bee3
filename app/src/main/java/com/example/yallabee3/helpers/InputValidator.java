package com.example.yallabee3.helpers;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

public class InputValidator {

    public static boolean addProductValidation(Context context, EditText titleEditText, EditText descriptionEditText ,EditText priceEditText , EditText phoneEditText, EditText placeEditText) {
        String title = titleEditText.getText().toString().trim();
        String desc = descriptionEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String place = placeEditText.getText().toString().trim();


        if (title.isEmpty() || desc.isEmpty() || price.isEmpty() || phone.isEmpty() || place.isEmpty())
        {

            if (title.isEmpty()) {
                titleEditText.setError("Title is require");
            }
            if (desc.isEmpty()) {
                descriptionEditText.setError("Description is require");
            }
            if (price.isEmpty()) {
                priceEditText.setError("Price is require");
            }
            if (phone.isEmpty()) {
                titleEditText.setError("phone is require");
            }
            if (place.isEmpty()) {
                titleEditText.setError("place is require");
            }
            return false;
        }
        return true;
    }

    public static boolean signUpValidation(Context context, EditText userName, EditText emailET, EditText passwordET, EditText confirmPasswordET,EditText phoneET) {

        String name = userName.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || phone.isEmpty() || password.isEmpty() || phone.length() < 11 || confirmPassword.isEmpty() || password.length() < 6 || !password.equals(confirmPassword)) {

            if (name.isEmpty())
                userName.setError("User Name Required", null);


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailET.setError("Email Not Valid", null);

            if (email.isEmpty())
                emailET.setError("Email Required", null);

            if (phone.isEmpty())
                emailET.setError("Email Required", null);

            if (phone.length() < 11)
                passwordET.setError("Phone should be larger than 11 Numbers", null);

            if (password.length() < 6)
                passwordET.setError("Password should be larger than 6 characters", null);

            if (password.isEmpty())
                passwordET.setError("Password Required", null);


            if (!(password.equals(confirmPassword)))
                passwordET.setError("Password does not match", null);

            if (confirmPassword.isEmpty())
                confirmPasswordET.setError("Confirm Password Required", null);


            return false;
        }
        return true;
    }

    public static boolean signInValidation(Context context, EditText emailET, EditText passwordET) {

        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {

            if (email.isEmpty())
                emailET.setError("Email Required", null);

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailET.setError("Email not valid", null);

            if (password.isEmpty())
                passwordET.setError("Password required", null);

            return false;
        }
        return true;
    }

    }
