import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
    ): boolean | UrlTree {
     
      const publicRoutes = ["/login", "/register", "/"]
      const isOnRoute = publicRoutes.includes(state.url)
      const isLogged = this.authService.isLoggedIn()      

      if(isOnRoute && isLogged) {
        // If public route and logged in, redirect
        return this.router.createUrlTree(['/posts']);
      }
      
      if (!isOnRoute && !isLogged) {
        // If private route and not logged in, redirect
        return this.router.createUrlTree(['']);
      }
      else {      
        return true
      }
    }
  
}
