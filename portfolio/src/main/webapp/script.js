// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomFact() {
  const facts =
      ['The first computer virus was created in 1983', 'The word computer “bug” was inspired by a real bug. It was founded by Grace Hopper in 1947', 'The word computer “bug” was inspired by a real bug. It was founded by Grace Hopper in 1947.'];

  // Pick a random fact
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

function showComments(){
    // List of comments
    const comments_list_container = document.getElementById('comments_list');
    fetch('/data').then(response => response.json()).then((comments_list) => {
    comments_list.forEach((comment) => {
      if(comment.imageURL){
          var image = document.createElement('img'); 
          image.src =  comment.imageURL;
          image.heigth = 150;
          image.width = 150;
          comments_list_container.appendChild(image);
      }
      comments_list_container.innerHTML += "<br>" + "<U>" + comment.userEmail + "</U>" + " : " + comment.commentMsg + "<br>" + "<br>" + "<br>";
        });
    });
}

function checkUserStatus(){

    // Check user login status and accordingly hide/unhide comments form
    fetch('/user-auth-status').then(response => response.json()).then((userLoginStatus) => {
        if(userLoginStatus.isLoggedIn){
            console.log("loggedin")
            const logoutURL = document.getElementById('logout_url');
            logoutURL.href = userLoginStatus.urlToRedirect;

            const comment_form = document.getElementById('comments');
            comment_form.hidden = false;

            const logout_message = document.getElementById('logout_message');
            logout_message.hidden = false;
        }
        else{
            console.log("loggedout")
            const loginURL = document.getElementById('login_url');
            loginURL.href = userLoginStatus.urlToRedirect;

            const login_message = document.getElementById('login_message');
            login_message.hidden = false;
        }
    });
}

function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const commentForm = document.getElementById('comment_form');
        commentForm.action = imageUploadUrl;
      });
}
