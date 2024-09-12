import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { passwordValidator } from 'src/app/shared/validators/password-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!:FormGroup;
  message: string = '';
  messageClass: string = ''

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
  ) {}

  // Initialize registration form with validators
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required, passwordValidator()]]
    })
  }

  // Handle form submission for user registration
  onSubmit(): void{
    if(this.registerForm.invalid) return;
    const { email, username, password } = this.registerForm.value
    this.authService.register({email, username, password}).subscribe({
      next: (() => this.router.navigate(['/posts'])),
      error: (error => {
        this.message = error
        this.messageClass = 'error'
      })
    })
  }
}
