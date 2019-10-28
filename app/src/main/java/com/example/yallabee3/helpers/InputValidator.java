package com.example.yallabee3.helpers;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

public class InputValidator {

    public static boolean addProductValidation(Context context, EditText titleEditText, EditText descriptionEditText, EditText priceEditText, EditText phoneEditText) {
        String title = titleEditText.getText().toString().trim();
        String desc = descriptionEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || price.isEmpty() || phone.isEmpty()) {

            if (title.isEmpty()) {
                titleEditText.setError("Title is required");
            }
            if (desc.isEmpty()) {
                descriptionEditText.setError("Description is required");
            }
            if (price.isEmpty()) {
                priceEditText.setError("Price is required");
            }
            if (phone.isEmpty()) {
                phoneEditText.setError("phone is required");
            }

            return false;
        }
        return true;
    }

    public static boolean signUpValidation(Context context, EditText userName, EditText emailET, EditText passwordET, EditText confirmPasswordET, EditText phoneET) {

        String name = userName.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || phone.isEmpty() || password.isEmpty() || phone.length() < 11 || confirmPassword.isEmpty() || password.length() < 6 || !password.equals(confirmPassword)) {

            if (name.isEmpty()) {
                userName.setError("User Name Required", null);
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailET.setError("Email Not Valid", null);
            }

            if (email.isEmpty()) {
                emailET.setError("Email Required", null);
            }

            if (phone.isEmpty()) {
                phoneET.setError("Phone Required", null);
            }

            if (phone.length() < 11) {
                phoneET.setError("Phone should be larger than 11 Numbers", null);
            }

            if (password.length() < 6) {
                passwordET.setError("Password should be larger than 6 characters", null);
            }
            if (password.isEmpty()) {
                passwordET.setError("Password Required", null);
            }

            if (!(password.equals(confirmPassword))) {
                confirmPasswordET.setError("Password does not match", null);
            }

            if (confirmPassword.isEmpty()) {
                confirmPasswordET.setError("Confirm Password Required", null);
            }

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


    public static boolean addSponsorValidation(Context context, EditText titleEditText, EditText priceEditText, EditText phoneEditText, EditText locationEditText, EditText descriptionEditText) {
        String title = titleEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String desc = descriptionEditText.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || price.isEmpty() || phone.isEmpty() || location.isEmpty()) {

            if (title.isEmpty()) {
                titleEditText.setError("Title is required");
            }
            if (desc.isEmpty()) {
                descriptionEditText.setError("Description is required");
            }
            if (price.isEmpty()) {
                priceEditText.setError("Price is required");
            }
            if (phone.isEmpty()) {
                phoneEditText.setError("phone is required");
            }
            if (location.isEmpty()) {
                locationEditText.setError("location is required");
            }

            return false;
        }
        return true;
    }


}
