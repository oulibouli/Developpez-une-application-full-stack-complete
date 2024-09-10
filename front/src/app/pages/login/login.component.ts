import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  message: string = '';
  messageClass: string = ''

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
        identifier: ['', [Validators.required]],
        password: ['', [Validators.required]],
    })
  }

  onSubmit() {
    if (this.loginForm.invalid) return;
    const { identifier, password } = this.loginForm.value
    this.authService.login({identifier, password}).subscribe({
      next: () => {
        // If succeed, redirect
        this.router.navigate(['/posts'])
      },
      error: () => {
        this.message = 'Identifiant ou mot de passe incorrect'
        this.messageClass= 'error'
      }
    })
  }

}
