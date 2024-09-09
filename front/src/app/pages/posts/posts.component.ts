import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/core/models/post.type';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts:Post[] = []
  constructor(
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (response: Post[]) => {
        this.posts = response;
        
      },
      error: (error) => {
        console.log(error);
        
      }
    })
  }

}
