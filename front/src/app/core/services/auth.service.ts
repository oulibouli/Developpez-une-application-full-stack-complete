import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { LoginRequest, RegisterRequest } from '../models/auth.type';
import { UserInfo } from '../models/user.type';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiLogin = 'http://localhost:8080/api/auth/login'
  private apiRegister = 'http://localhost:8080/api/auth/register'
  private apiTokenExpiration = 'http://localhost:8080/api/auth/tokenexpiration'
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  login(loginRequest: LoginRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.apiLogin, loginRequest)
      .pipe(
        tap(response => {
          // Get the token and save it in localStorage
          localStorage.setItem('token', response.token)
        }),
        catchError((error) => {
          console.log('Login failed', error);
          return throwError(() => new Error(error))
        })
      )
  }

  register(registerRequest: RegisterRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.apiRegister, registerRequest)
      .pipe(
        tap(response => {
          return response
        }),
        catchError((error) => {
          return throwError(() => new Error(error))
        })
      )
  }

  isTokenExpired(token: string): boolean {
    const decodedToken: any = jwtDecode(token);
    const expirationDate = decodedToken.exp * 1000
    const currentDate = new Date().getTime()
    
    return currentDate > expirationDate
  }

  logout() {
    localStorage.removeItem('token')
    this.router.navigate(['/login'])
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token')
  }
}
