import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { LoginRequest, RegisterRequest, UpdateRequest } from '../models/auth.type';
import { UserInfo } from '../models/user.type';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(this.checkToken())
  isLoggedIn$ = this.loggedIn.asObservable()
  private apiLogin = 'http://localhost:8080/api/auth/login'
  private apiRegister = 'http://localhost:8080/api/auth/register'
  private apiMe = 'http://localhost:8080/api/auth/me'
  private apiUpdate = 'http://localhost:8080/api/auth/update'
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  checkToken(): boolean {
    return !!localStorage.getItem('token')
  }
  login(loginRequest: LoginRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.apiLogin, loginRequest)
      .pipe(
        tap(response => {
          // Get the token and save it in localStorage
          localStorage.setItem('token', response.token)
          this.loggedIn.next(true)
        }),
        catchError(error => {
          console.log('Login failed', error);
          return throwError(() => new Error(error))
        })
      )
  }

  register(registerRequest: RegisterRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.apiRegister, registerRequest)
      .pipe(
        catchError(error => {
          return throwError(() => new Error(error))
        })
      )
  }

  userInfos(): Observable<UserInfo> {
    return this.http.get<UserInfo>(this.apiMe)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      })
    )
  }

  update(updateRequest: UpdateRequest): Observable<UpdateRequest> {
    return this.http.put<UpdateRequest>(this.apiUpdate, updateRequest)
    .pipe(
      catchError(error => {
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
    this.loggedIn.next(false);
    this.router.navigate(['/login'])
  }

  isLoggedIn(): boolean {  
    return this.loggedIn.value
  }
}
