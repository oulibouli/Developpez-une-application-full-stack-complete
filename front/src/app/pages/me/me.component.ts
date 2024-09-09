import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserInfo } from 'src/app/core/models/user.type';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  userInfo!:UserInfo
  meForm!:FormGroup

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.meForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required]]
    })
    this.me()
  }

  me() {
    this.authService.userInfos().subscribe({
      next: (response) => {
        this.userInfo = response
        this.meForm.patchValue({
          username: this.userInfo.username,
          email: this.userInfo.email
        })
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  onSubmit() {
    if(this.meForm.invalid) return;
    const { username, email } = this.meForm.value
    this.authService.update({username, email}).subscribe({
      next: () => {
        alert('Modification réussie. Veuillez vous reconnecter.')
        this.authService.logout()
      },
      error: (error) => {
        console.log(error);
      }
    })
    
  }

  logout() {
    this.authService.logout()
  }
}
