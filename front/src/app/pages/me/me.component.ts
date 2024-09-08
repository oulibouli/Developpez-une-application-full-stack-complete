import { Component, OnInit } from '@angular/core';
import { UserInfo } from 'src/app/core/models/user.type';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  userInfo!:UserInfo

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.me()
  }

  me() {
    this.authService.userInfos().subscribe({
      next: (response) => {
        this.userInfo = response
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

}
