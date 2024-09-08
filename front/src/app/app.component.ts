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
    this.checkTokenExpiration()
  }

  private checkTokenExpiration(): void {
    let token = localStorage.getItem('token')
    if(token !== null) {
      const expired = this.authService.isTokenExpired(token)
      console.log(expired);
      
      if(expired) {
        localStorage.removeItem('token')
      }
    }
  }

  showBanner(): boolean {    
    return this.router.url !== '/';
  }
}
