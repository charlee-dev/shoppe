# \[ 🚧 Work in progress 👷‍♀️⛏👷🔧️👷🔧 🚧 \]

![visitors](https://visitor-badge.laobi.icu/badge?page_id=shoppe)
![](https://img.shields.io/github/stars/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/forks/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/watchers/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/commit-activity/m/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/last-commit/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/repo-size/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/tokei/lines/github/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/languages/count/adrianwitaszak/shopee-kmm)
![](https://img.shields.io/github/languages/top/adrianwitaszak/shopee-kmm)

# Shoppe Kotlin Multiplatform App

Kotlin and Ktor app, which can easily be deployed to Heroku.

This application supports the [Getting Started with Java on Heroku](https://devcenter.heroku.com/articles/getting-started-with-java) article - check it out.

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku Toolbelt](https://toolbelt.heroku.com/).

```sh
$ git clone https://github.com/orangy/ktor-heroku-start.git
$ cd ktor-heroku-start
$ gradlew install
$ heroku local:start
```

Your app should now be running on [localhost:8080](http://localhost:8080/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
MONGO_URI=Your_Mongo_Database_Access_Key
PORT=YOUR_PORT

```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```
