
# 开发规范指南

为保证代码质量、可维护性、安全性与可扩展性，请在开发过程中严格遵循以下规范。

## 一、项目基本信息

- **项目名称**：家谱管理系统 (Genealogy)
- **工作目录**：`D:\IdeaProject\Genealogy`
- **操作系统**：Windows 11
- **代码作者**：xiongyou
- **开发语言**：Java 8
- **构建工具**：Maven
- **使用框架**：Spring Boot 2.7.18 + JDK 1.8.0_221

## 二、目录结构说明

```
Genealogy
├── sql
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── genealogy
    │   │           ├── common
    │   │           │   ├── constant
    │   │           │   ├── exception
    │   │           │   ├── response
    │   │           │   ├── util
    │   │           │   └── validation
    │   │           ├── config
    │   │           ├── controller
    │   │           ├── dto
    │   │           ├── entity
    │   │           ├── query
    │   │           ├── repository
    │   │           └── service
    │   │               └── impl
    │   └── resources
    └── test
        └── java
            └── com
                └── genealogy
                    ├── controller
                    └── service
                        └── impl
```

## 三、技术栈要求

- **主框架**：Spring Boot 2.7.18
- **语言版本**：Java 8
- **核心依赖**：
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `mysql-connector-java`
  - `lombok`
  - `springfox-boot-starter`
  - `spring-boot-starter-validation`
  - `mybatis-spring-boot-starter`
  - `spring-boot-starter-security`
  - `jackson-databind`

## 四、分层架构规范

| 层级        | 职责说明                         | 开发约束与注意事项                                               |
|-------------|----------------------------------|----------------------------------------------------------------|
| **Controller** | 处理 HTTP 请求与响应，定义 API 接口 | 不得直接访问数据库，必须通过 Service 层调用                  |
| **Service**    | 实现业务逻辑、事务管理与数据校验   | 必须通过 Repository 层访问数据库；返回 DTO 而非 Entity（除非必要） |
| **Repository** | 数据库访问与持久化操作             | 继承 `JpaRepository`；使用 `@EntityGraph` 避免 N+1 查询问题     |
| **Entity**     | 映射数据库表结构                   | 不得直接返回给前端（需转换为 DTO）；包名统一为 `entity`         |

### 接口与实现分离

- 所有接口实现类需放在接口所在包下的 `impl` 子包中。

## 五、安全与性能规范

### 输入校验

- 使用 `@Valid` 与 JSR-303 校验注解（如 `@NotBlank`, `@Size` 等）
  - 注意：Spring Boot 2.7 中校验注解位于 `javax.validation.constraints.*`

- 禁止手动拼接 SQL 字符串，防止 SQL 注入攻击。

### 事务管理

- `@Transactional` 注解仅用于 **Service 层**方法。
- 避免在循环中频繁提交事务，影响性能。

## 六、代码风格规范

### 命名规范

| 类型       | 命名方式             | 示例                  |
|------------|----------------------|-----------------------|
| 类名       | UpperCamelCase       | `UserServiceImpl`     |
| 方法/变量  | lowerCamelCase       | `saveUser()`          |
| 常量       | UPPER_SNAKE_CASE     | `MAX_LOGIN_ATTEMPTS`  |

### 注释规范

- 所有类、方法、字段需添加 **Javadoc** 注释。
- 使用中文注释，符合用户第一语言。

### 类型命名规范（阿里巴巴风格）

| 后缀 | 用途说明                     | 示例         |
|------|------------------------------|--------------|
| DTO  | 数据传输对象                 | `UserDTO`    |
| DO   | 数据库实体对象               | `UserDO`     |
| BO   | 业务逻辑封装对象             | `UserBO`     |
| VO   | 视图展示对象                 | `UserVO`     |
| Query| 查询参数封装对象             | `UserQuery`  |

### 实体类简化工具

- 使用 Lombok 注解替代手动编写 getter/setter/构造方法：
  - `@Data`
  - `@NoArgsConstructor`
  - `@AllArgsConstructor`

## 七、扩展性与日志规范

### 接口优先原则

- 所有业务逻辑通过接口定义（如 `UserService`），具体实现放在 `impl` 包中（如 `UserServiceImpl`）。

### 日志记录

- 使用 `@Slf4j` 注解代替 `System.out.println`

## 八、通用规则总结

1. **包结构清晰**：遵循标准的分层结构，便于维护和扩展。
2. **依赖管理**：使用 Maven 进行依赖管理，确保版本一致性。
3. **测试驱动开发**：单元测试覆盖关键业务逻辑，提升代码可靠性。
4. **数据库配置**：采用 MySQL 数据库，并启用 Hibernate 的 DDL 自动建表功能（尽管当前设置为 none）。
5. **API 文档支持**：集成 Swagger UI 支持 API 文档自动生成。
6. **安全机制**：引入 Spring Security 提供基础的安全控制能力。
7. **国际化支持**：虽然未明确提及，但建议未来支持多语言环境。

## 九、编码原则总结

| 原则       | 说明                                       |
|------------|--------------------------------------------|
| **SOLID**  | 高内聚、低耦合，增强可维护性与可扩展性     |
| **DRY**    | 避免重复代码，提高复用性                   |
| **KISS**   | 保持代码简洁易懂                           |
| **YAGNI**  | 不实现当前不需要的功能                     |
| **OWASP**  | 防范常见安全漏洞，如 SQL 注入、XSS 等      |
