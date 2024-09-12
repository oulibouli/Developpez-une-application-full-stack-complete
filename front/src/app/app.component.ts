import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  constructor(
    private router: Router,
    private authService: AuthService
  ) {}
  title = 'front';

  ngOnInit(): void {
    this.authService.checkTokenExpiration() // Check token expiration on component load
  }

  // Method to conditionally show or hide the banner based on the current route
  showBanner(): boolean {    
    return this.router.url !== '/'; // Show the banner if the current route is not the home page
  }
}
