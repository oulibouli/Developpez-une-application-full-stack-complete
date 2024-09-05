import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { LoginRequest } from '../models/auth.type';
import { UserInfo } from '../models/user.type';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api = 'http://localhost:8080/api/auth/login'
  constructor(
    private http: HttpClient
  ) {}

  login(loginRequest: LoginRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.api, loginRequest)
      .pipe(
        tap((response) => {
          // Get the token and save it in localStorage
          localStorage.setItem('token', response.token)
        }),
        catchError((error) => {
          console.log('Login failed', error);
          return throwError(() => new Error(error))
        })
      )
  }

  isLoggedIn(): boolean {
    return !localStorage.getItem('token')
  }
}
