import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/core/models/post.type';
import { PostsService } from 'src/app/core/services/posts.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {
  post!: Post
  newComment: string =''

  constructor(
    private route: ActivatedRoute,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.getPost();
  }

  getPost(){
    const id = this.route.snapshot.paramMap.get('id');

    if(id) {
      this.postService.getPostById(parseInt(id)).subscribe(post => this.post = post)
    }
    else console.error('ID is null');
  }

  sendComment() {

    const description = this.newComment;
    const postId = this.post.id;
    const date = new Date().toISOString()

    this.postService.addComment({ description, date }, postId).subscribe(comment => {
      this.post.comments.push(comment)
      this.newComment = ''
    })
  }
}
