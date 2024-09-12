import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

// Validator function for password strength validation
export function passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value

        if(!value) return null

        // Check for at least one number
        const hasNumber = /\d/.test(value);

        // Check for at least one uppercase letter
        const hasUpperCase = /[A-Z]/.test(value);

        // Check for at least one lowercase letter
        const hasLowerCase = /[a-z]/.test(value);

        // Check for at least one special character
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);

        // Ensure the password is at least 8 characters long
        const isValidLength = value.length >= 8;

        // Password is valid if all conditions are met
        const passwordValid = hasNumber && hasUpperCase && hasLowerCase && hasSpecialChar && isValidLength;
    
        // Return an error object if the password does not meet the criteria
        return !passwordValid ? { passwordStrength: true } : null
    }
}