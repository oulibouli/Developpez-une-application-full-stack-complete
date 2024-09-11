import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value

        if(!value) return null

        const hasNumber = /\d/.test(value);
        const hasUpperCase = /[A-Z]/.test(value);
        const hasLowerCase = /[a-z]/.test(value);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
        const isValidLength = value.length >= 8;

        const passwordValid = hasNumber && hasUpperCase && hasLowerCase && hasSpecialChar && isValidLength;
    
        return !passwordValid ? { passwordStrength: true } : null
    }
}