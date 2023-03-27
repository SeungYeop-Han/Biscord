@REM init nginx
cd nginx && mkdir logs && mkdir temp && start nginx

@REM init, build, and start react app
start npm install && npm run build && npm start