<p align="center">
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/icon.jpg" height="92" />
</p>
<h1 align="center">Website Admin Finder</h1>

![License](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)

Website Admin Finder allows you to audit the security of websites in search of administration panels automatically.

![License](https://img.shields.io/badge/License-GPL%2F3.0-orange?style=flat)
[![Pull requests](https://img.shields.io/github/issues-pr/SimulatedRealitySoft/WebsiteAdminFinder.svg?style=flat)](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/pulls)
![MinSdk](https://img.shields.io/badge/Minimum%20SDK-21%20(Lollipop)-839192?style=flat&logo=android&logoColor=green)
[![Latest tag](https://img.shields.io/github/tag/SimulatedRealitySoft/WebsiteAdminFinder.svg?style=flat)](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/tags)
![Permissions](https://img.shields.io/badge/permissions-3-brightgreen?style=flat&logo=iconify&logoColor=green)

## README Translation
- [Español](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/LEEME.md)
- <b>English</b>
### About

**Website Admin Finder** It's a lightweight application for Android that allows you to audit the security of websites in search of the admin panel automatically. It relies on brute force requests to find the panel.

I didn't find a similar application or software, I needed to have this type of tools within my reach, so I thought about developing it, and why not, making it public, free and open source.

### Characteristics
- <b>light</b>: It doesn't use too many resources and avoided using too many vectors to conserve size and improve performance.<br /><br />
- <b>Multiple scan</b>: you can import your own wordlist of websites to scan more than one site at a time.<br /><br />
- <b>Real time log</b>: Shows a log in real time to notify you that you have found a website.<br /><br />
- <b>Background service</b>: You don't want to have the application in the foreground or wait? No problem; With notifications you will be notified of the results.<br /><br />
- <b>Persistence</b>: Did you forget which website you scaned or which administration panels you could find? Don't worry, the application keeps a record of the log and the positive results as well.<br /><br />
- <b>Exact time</b>: If you need to know the exact time of each request, the application can save the exact hour, minutes, seconds and even milliseconds.

<summary><h3 style="display: inline">Screenshots</h3></summary>

<p align="center">
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/01_en-US.jpg" height="500"/>
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/02_en-US.jpg" height="500"/>
<img src="https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/blob/main/fastlane/metadata/android/en-US/images/phoneScreenshots/03_en-US.jpg" height="500"/>
</p>

### Create WorldList

To scan multiple sites at the same time, you can create a WorldList that contains the url of the websites you want to scan. The "syntax" is simple, first create a txt file in storage, open it with any text editor and just make sure to stack the links on top of each other. 

A simple example would be:

*WordList.txt*

<code>https://example1.com
https://example2.net
https://ejemplo.xyz
</code>

### Attention

**Notice** that the speed of the analysis will not be based on the programming logic of the application. 40% is based on your internet speeds and 60% is based on the website's servers. As far as I know, there is nothing that we can do with that.

### Credits

Developed by **Diego Calveyra**.<br/>
2023.

### Contact

You can contact me at [SimulatedRealitySoft@gmail.com](mailto:[simulatedrealitysoft@gmail.com).
Feel free to send your questions, suggestions or related topics.
I will be happy to answer all kinds of messages. Please know that I will ignore vulgar, harmful or hateful messages.

##### message to unknown
I never understood why.

## Downloads

At the moment you can download the compiled and signed project (****APK****) from *GitHub*.

[<img src="https://raw.githubusercontent.com/Unknown-60/Unknown-60.github.io/main/assets/get-it-on-github.png"
     alt="Get it on GitHub"
     height="80">](https://github.com/SimulatedRealitySoft/WebsiteAdminFinder/releases/latest)

### Licence

> Website Admin Finder (c) 2023 Diego Calveyra
> 
> This is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
> 
> This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
> 
> You should have received a copy of the GNU General Public License along with this app. If not, see https://www.gnu.org/licenses/.
