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

  // Check if a token is present in localStorage
  checkToken(): boolean {
    return !!localStorage.getItem('token')
  }
  // Login method, sends loginRequest and sets token in localStorage on success
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

  // Register method, saves token on success
  register(registerRequest: RegisterRequest): Observable<UserInfo> {
    return this.http.post<UserInfo>(this.apiRegister, registerRequest)
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.token)
          this.loggedIn.next(true)
        }),
        catchError(error => {
          return throwError(() => new Error(error))
        })
      )
  }

  // Fetch user information
  userInfos(): Observable<UserInfo> {
    return this.http.get<UserInfo>(this.apiMe)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      })
    )
  }

  // Update user profile
  update(updateRequest: UpdateRequest): Observable<UpdateRequest> {
    return this.http.put<UpdateRequest>(this.apiUpdate, updateRequest)
    .pipe(
      catchError(error => {
        return throwError(() => new Error(error))
      })
    )
  }

  // Check if the token is expired based on its expiration time
  isTokenExpired(token: string): boolean {
    const decodedToken: any = jwtDecode(token);
    const expirationDate = decodedToken.exp * 1000
    const currentDate = new Date().getTime()
    
    return currentDate > expirationDate
  }

  // Logout the user by removing the token and updating loggedIn state
  logout() {
    localStorage.removeItem('token')
    this.loggedIn.next(false);
    this.router.navigate(['/login'])
  }

  // Private method to check if the user's token is expired
  checkTokenExpiration(): void {
    let token = localStorage.getItem('token') // Get token from local storage

    if(token && this.isTokenExpired(token)) {
      this.logout() // Logout if the token is expired
    }
  }

  // Return the current login status
  isLoggedIn(): boolean {  
    return this.loggedIn.value
  }
}
