# Springboot Trading App UI with React

Table of Contents
- (Introduction)[#introduction]
- (Quick Start)[#quick-start]
- (Usage)[#usage]
- (Improvements)[#improvements]

## Introduction

The purpose of this app is to provide a web-based UI for interacting with the Springboot trading API
which was developed in a previous Jarvis project. This project uses Node.js' standalone web server,
and is based on the `create-react-app` template.

This project serves as an introduction to modern Javascript (using ES2015 standards) using Node.js and React, as well as
some rudimentary HTML and CSS.

## Quick Start

- Requirements:
  - `Node.js` version 8.10 or higher
  - `npm` version 6 or higher
  - (Springboot Stock Trading Web API)[../springboot] running (This app assumes the API is hosted at localhost:8080)
  - Any modern web browser
- Development version
  - Navigate to the project directory and execute `npm start`, or the equivalent `yarn` command.
  - Server will be hosted on `localhost:3000`.
- Production version
  - Navigate to the project directory and execute `npm run-script build`, or the equivalent `yarn` command.
  - Copy the contents of the `build/` directory to a pre-configured web root, such as `/var/www/html/` for `httpd` servers.
  - App URL will depend on web server configuration and location within the web root.
  
## Usage

This app is designed to be used as an administrative console for interacting with the Springboot API project in this repository, similar to that project's `swagger-ui`. The various endpoints are organized into groups based on the services they provide, and each group may be accessed using buttons on the left of the page. The following functions are available within each group.

- Account Services
  - Create and delete trader accounts
  - Deposit and withdraw simulation money from traders' accounts
- Quote Services
  - Retrieve a quote from third party market data provider, IEX.
  - Retrieve internally-stored quotes.
  - Track new market symbols.
  - Update symbols using IEX data, or perform manual updates.
- Order Services
  - Place buy or sell orders for certain traders.
- User Dashboards
  - Access trader profiles and portfolios.
  
## Improvements
- Add a built-in web server to the project, such as `Express`, or `Serve`
- Add environment variables, CLI arguments, or `.env` file support to allow changing the Springboot API hosting location
- Create a "user mode" web page that is overall more useful.
- Visual design/CSS improvements.
