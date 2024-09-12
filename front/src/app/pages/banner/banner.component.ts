import { Component, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.scss']
})
export class BannerComponent implements OnInit {
  isLogged: boolean = false
  isMenuActive = false; // Variable pour l'état du menu

  toggleMenu() {
    this.isMenuActive = !this.isMenuActive;
  }
  
  private unsubscribe$ = new Subject<void>(); // Create a Subject to unsubscribe the Observable

  constructor(
    private authService: AuthService
  ) { }

  // Subscribe to the logged-in state when the component initializes
  ngOnInit(): void {
    this.authService.isLoggedIn$
    .pipe(takeUntil(this.unsubscribe$))
    .subscribe(isLoggedIn => {
      this.isLogged = isLoggedIn
    })
  }

  // Unsubscribe from the observable when the component is destroyed
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
