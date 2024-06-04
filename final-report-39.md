# CS304 Team Project - Sprint 2 Report



## 1.项目指标 (Metrics)

### 代码行数 (Lines of Code)
- **总代码行数**: 6753
- **注释行数**: 
- **空白行数**: 
![alt text](image-7.png)
![alt text](image-8.png)
### 包/模块数量 (Number of packages/modules)
- **总包/模块数量**: 9

### 源文件数量 (Number of source files)
- **总源文件数量**: 77

### 依赖数量 (Number of dependencies)
- **总依赖数量**: 17

## 2.文档 (Documentation)

### 用户文档 (End User Documentation)

- **链接**: [Readme.md](https://github.com/sustech-cs304/team-project-24spring-39/blob/main/README.md)


### 开发者文档 (Developer Documentation)

- **链接**: [CS304.md](https://github.com/sustech-cs304/team-project-24spring-39/blob/backend_wyj/CS304.md)


## 3.测试 (Tests)

### 自动化测试技术/工具/框架 (Technology/Tools/Framework)
- **使用的技术/工具/框架**: Junit5、Mockito、Springboot test
- **测试代码或相关工件**: 
 ![alt text](image.png)
 ![alt text](image-2.png)
```
mvn test --fail--never
jacoco:report
```
### 测试有效性 (Effectiveness of Tests)
- **测试覆盖率**: [覆盖率百分比]
-  ![alt text](image-1.png)
- **测试报告**: 本地展示文件

## 4.构建 (Build)

### 构建技术/工具/框架 (Technology/Tools/Framework)
- **使用的技术/工具/框架**: Maven、npm
- **构建任务**：
```
mvn package -DskipTests
npm run build
```
![alt text](image-5.png)

### 构建产物 (Build Artifacts)
- **最终产物**: CS304-0.0.1-SNAPSHOT.jar、dist
- **构建文件**: pom.xml
## 5.部署 (Deployment)

### 容器化技术/工具/框架 (Technology/Tools/Framework)
- **使用的技术/工具/框架**: Docker
- **容器化脚本**: ![alt text](image-3.png)

### 成功容器化证明 (Proof of Containerization)
- **容器化成功截图**: ![alt text](image-4.png)
- ![alt text](image-6.png)
- ![alt text](image-9.png)



