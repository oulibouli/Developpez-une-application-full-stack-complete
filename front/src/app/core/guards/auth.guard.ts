import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ){}
  // Guard method to determine if a route can be activated
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
    ): boolean | UrlTree {
     
      const publicRoutes = ["/login", "/register", "/"]
      const isOnRoute = publicRoutes.includes(state.url)
      const isLogged = this.authService.isLoggedIn()            

      if(isOnRoute && isLogged) {
        // If user is logged in and on a public route, redirect to /posts
        return this.router.createUrlTree(['/posts']);
      }
      
      if (!isOnRoute && !isLogged) {
         // If the route is private and user is not logged in, redirect to home
        return this.router.createUrlTree(['']);
      }
      else {     
        // Allow access if all conditions are met 
        return true
      }
    }
  
}
