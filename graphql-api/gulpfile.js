const gulp = require("gulp");
const del = require("del");
const ts = require("gulp-typescript");
const nodemon = require("gulp-nodemon");
const tslint = require("gulp-tslint");
 

const tsProject = ts.createProject("tsconfig.json");

gulp.task("watch", function () {
  return gulp.watch("src/**/*.*", gulp.parallel("compile"));
});

gulp.task('clean', function () {
  return del(['build']);
});

gulp.task("compile", function () {
  return tsProject.src() 
    .pipe(tsProject())
    .js.pipe(gulp.dest("build"));
});

gulp.task('copy',  function() {
    return gulp.src(['src/**/*.proto','src/**/cert/*','src/**/*.graphql'])
      .pipe(gulp.dest('build'))
});

gulp.task('copyProtoFolder',  function() {
  return gulp.src(['../proto/*.proto','src/**/cert/*','src/**/*.graphql'])
    .pipe(gulp.dest('build'))
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



gulp.task("default", gulp.parallel("compile", "watch", "nodemon"));

gulp.task("build", gulp.series("clean", "compile" , "copy"));