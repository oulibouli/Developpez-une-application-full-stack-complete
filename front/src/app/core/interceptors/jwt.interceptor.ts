import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // Intercept outgoing HTTP requests to attach JWT token in the headers
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get the token from the localStorage
    const token = localStorage.getItem('token')

    if(token) {
      // Clone request and add Authorization header if token exists
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    }
    return next.handle(request).pipe(
      catchError(err => {
        if (err.status === 401) {
          // If unauthorized, logout and redirect to login
          this.authService.logout()
          this.router.navigate(['/login'])
        }
        return throwError(() => new Error(err))
      })
    );
  }
}
