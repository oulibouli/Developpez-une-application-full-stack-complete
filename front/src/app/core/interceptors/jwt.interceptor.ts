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

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get the token from the localStorage
    const token = localStorage.getItem('token')

    if(token) {
      // Clone the request to add the Authorization header
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    }
    return next.handle(request).pipe(
      catchError(err => {
        if (err.status === 401) {
          // Redirect to login page
          this.authService.logout()
          this.router.navigate(['/login'])
        }
        return throwError(() => new Error(err))
      })
    );
  }
}
