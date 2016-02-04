# Project 1 - Instagram Photo Viewer

Instagram Photo Viewer is an android app that allows a user to check out popular photos from Instagram. The app utilizes Instagram API to display images and basic image information to the user.

Time spent: 15 hours spent in total

## User Stories

  * :The following **required** functionality is completed:
    *  :white_check_mark: User can scroll through current popular photos from Instagram (5 points)
    * For each photo displayed, user can see the following details:
      * :white_check_mark: Graphic, Caption, Username (2 points)
      * :white_check_mark: (Optional) relative timestamp, like count, user profile image (3 points)
  * The following **advanced** features are implemented:
    * :white_check_mark: Advanced: Add pull-to-refresh for popular stream with SwipeRefreshLayout (1 point)
    * :white_check_mark: Advanced: Show latest comment for each photo (bonus: show last 2 comments) (1 point + 1 bonus)
    * :white_check_mark: Advanced: Display each user profile image using a RoundedImageView (2 points)
    * :white_check_mark: Advanced: Display a nice default placeholder graphic for each image during loading (read more about Picasso) (1 point)
    * :white_check_mark: Advanced: Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
  * The following **bonus** features are implemented:
    * :white_check_mark: Bonus: Allow user to view all comments for an image within a separate activity or dialog fragment * :white_check_mark: (3 points)
    * :white_check_mark: Bonus: Allow video posts to be played in full-screen using the VideoView (2 points)
    * :white_check_mark: Bonus: Apply the popular Butterknife annotation library to reduce view boilerplate. (1 point)
  * The following **additional** features are implemented:
    * Videoview automatically plays videos
    * Image will overlay videoview until it's ready to play

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![General Functionality](https://github.com/franklinho/InstagramHomework/blob/master/InstagramHomeworkWalkthrough.gif)


GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

  * Ran into some issues with SwipeRefreshLayout.
  * Had to figure out how to recycle VideoViews in ListViews
  * When showing comments in DialogFragment, had to learn how to Parcel objects. No easy way to pass Arraylist through intents    


## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [RoundedImageView](https://github.com/vinc3m1/RoundedImageView) - Library that rounds images for profile pictures
- [Butterknife](http://jakewharton.github.io/butterknife/) - Removes boilerplate code by binding view IDs to objects
- [Joda Time Android](https://github.com/dlew/joda-time-android) - Date formatting and handling libarary
- [Parceler](https://github.com/johncarl81/parceler) - Simplifies parceling objects


## License

    Copyright 2016 Franklin Ho

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.