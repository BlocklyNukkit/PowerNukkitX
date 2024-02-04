### Version 2.0.0 of PowerNukkitX, work in progress...

### Start PowerNukkitX:
#### Windows
`click start.bat`
#### Linux
```shell
chmod +x start.sh
./start.sh
```

### Build Guide:
1. gradle/tasks/alpha build/build - Build all products, including source jar,jar,shaded jar,javadoc
2. gradle/tasks/alpha build/buildSkipChores - Build partial products, including jar and shaded jar, skip test
3. gradle/tasks/alpha build/buildFast - Only build `jar`, skip test
4. gradle/tasks/alpha build/clean - clean all build products