# Password Encoder

На Mac OS можно использовать стандартный LibreSSL 2.8.3.

```shell
$ content="Hello, world"
$ password="secret"

# encrypted=$(./encrypt.sh "$password" "$content")
$ encrypted=$(echo -n "$content" | 
  openssl enc -aes-256-cbc \
    -base64 -A \
    -md sha256 \
    -pass pass:$password)

$ echo "Encrypted value [$encrypted]"
Encrypted value [U2FsdGVkX1+eWX8x+srdjfVc/W7I9Lqpa2S3hunYtNE=]

$ ./gradlew clean build
BUILD SUCCESSFUL in 2s
4 actionable tasks: 4 executed

$ java -jar build/libs/password-encryptor.jar "$password" "$encrypted"
Hello, world
```