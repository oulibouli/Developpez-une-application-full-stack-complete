import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Topic } from 'src/app/core/models/topic.type';
import { UserInfo } from 'src/app/core/models/user.type';
import { AuthService } from 'src/app/core/services/auth.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  userInfo!:UserInfo
  meForm!:FormGroup
  topics: Topic[] = []
  message: string = '';

  constructor(
    private authService: AuthService,
    private topicService: TopicService,
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

    this.topicService.getTopicsByUser().subscribe({
      next: (response) => {
        this.topics = response
        
      },
      error: (error) => {
        console.error(error)
      }
    })
  }

  unsubscribe(topicId: number) {
    this.topicService.unsubscribeTopic(topicId).subscribe({
      next: (response) => {
        // Filter the list to exclude the topic
        this.topics = this.topics.filter(topic => topic.id !== topicId);
      },
      error: (error) => {
        console.error(error)
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
