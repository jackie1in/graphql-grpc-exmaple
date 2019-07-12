const gulp = require("gulp");
const del = require("del");
const ts = require("gulp-typescript");
const nodemon = require("gulp-nodemon");
const tslint = require("gulp-tslint");
// const install = require("gulp-install");
// const package = require("./package.json");
// const fs = require("fs")
 

const tsProject = ts.createProject("tsconfig.json");

gulp.task("default", ["compile", "watch", "nodemon"]);
gulp.task("build", ["clean", "compile" , "copy"]);

gulp.task("watch", function () {
  return gulp.watch("src/**/*.*", ["compile"]);
});

// 添加执行顺序,依赖clean结果
gulp.task("compile",["clean"], function () {
  return tsProject.src() 
    .pipe(tsProject())
    .js.pipe(gulp.dest("build"));
});

// 依赖compile执行结果
gulp.task('copy',["compile"],  function() {
    return gulp.src(['src/**/*.proto','src/**/cert/*','src/**/*.graphql'])
      .pipe(gulp.dest('build'))
});

gulp.task('copyProtoFolder',  function() {
  return gulp.src(['../proto/*.proto','src/**/cert/*','src/**/*.graphql'])
    .pipe(gulp.dest('build'))
});


// // Copy node modules from cache with cache refresh
// gulp.task("node_modules", ["node_modules_cache"], () => {
//   gulp.src("./build/modules/node_modules/**/*.*", {base: "./build/modules/node_modules/"})
//       .pipe(gulp.dest("./build/debug/resources/app/node_modules"));
// });

// gulp.task("node_modules_cache",['copy'], () => {
//   // Ensure directory exists
//   if(!fs.existsSync("./build/modules")){
//       fs.mkdirSync("./build/modules");
//   }

//   // You can replace following by just copy package.json, but I have already loaded it so let's just save
//   fs.writeFileSync("./build/modules/package.json", JSON.stringify(package));

//   // Make npm install in cache location
//   return gulp.src(["./package.json","./yarn.lock"])
//              .pipe(install({commands: {
//               'package.json': 'yarn'
//             },
//             yarn: ['--verbose', '--production']
//           }));
// });



gulp.task('clean', function () {
  return del(['build']);
});

gulp.task("nodemon", function () {
  nodemon({script: "build/server.js"});
});

gulp.task("tslint", () =>
  gulp.src("src/**/*.ts")
    .pipe(tslint({
      formatter: "verbose"
    }))
    .pipe(tslint.report())
);