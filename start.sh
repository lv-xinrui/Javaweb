#!/bin/bash

# 文华学院官网启动脚本

echo "=========================================="
echo "    文华学院官网 Spring Boot 项目启动"
echo "=========================================="

# 检查Java版本
echo "检查Java环境..."
if ! command -v java &> /dev/null; then
    echo "❌ 错误: 未找到Java环境，请安装JDK 17或更高版本"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ 错误: Java版本过低，需要JDK 17或更高版本，当前版本: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java版本检查通过: $(java -version 2>&1 | head -n 1)"

# 检查Maven
echo "检查Maven环境..."
if ! command -v mvn &> /dev/null; then
    echo "❌ 错误: 未找到Maven，请安装Maven 3.6或更高版本"
    exit 1
fi

echo "✅ Maven版本: $(mvn -version | head -n 1)"

# 检查MySQL连接（可选）
echo "检查数据库配置..."
if grep -q "localhost:3306" src/main/resources/application.yml; then
    echo "⚠️  提示: 请确保MySQL数据库已启动，并创建了wenhua_university数据库"
    echo "   创建数据库命令:"
    echo "   CREATE DATABASE wenhua_university CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
fi

# 编译项目
echo "编译项目..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ 编译失败，请检查代码错误"
    exit 1
fi

echo "✅ 编译成功"

# 启动应用
echo "启动Spring Boot应用..."
echo "访问地址: http://localhost:8080"
echo "按 Ctrl+C 停止应用"
echo "=========================================="

mvn spring-boot:run
