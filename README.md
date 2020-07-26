
<p align="center">
  <a href="https://github.com/Koooooo-7">
    <img alt="kbot" src="https://user-images.githubusercontent.com/33706142/86933539-369bd600-c16d-11ea-8d44-ee4c1f053cdf.jpg" width = "100px" >
  </a>
</p>

<p align="center">
  <b>Kbot</b>, a Quantum Discord bot in SpringBoot.
</p>



### [Intruduction](#Intruduction)  ·  [Usage](#bookusage)  ·  [Plugins](#wrenchplugins)  ·  [Contributing](##Contributing)  · [Statement](#-statement )

<!---
labels , star... something
-->

---

## 📣Introduction

**`K-bot`** is a Quantum Discord which means she can do everything, if she has enough plugins. :rocket:

It is NOT the specific bot that you just import it to your Discord server( but she can do that).

She is made in `SpringBoot` and makes all the plugins as plugins/configurations.  hence you can chose what skills you want for `k-bot` via setting the configuration files to summon the abilities, and you can register your own plugins easily. Besides that, you can give her your own calling name/command !

---

## :book:Usage

> IF YOU WANNA USE IT.

Set those configurations to `application.yml`(more details in `application-example.yml`).

**Get your bot**

```yaml
k-bot:
  # your bot token
  token: "your bot token"
  # the command you wanna call your bot
  cmd: "!koy"
```

**Summon  Plugin**

the plugins you want add to your bot and then , it will only create those plugin instances.

```yaml
  # register plugins
  plugins:
  - player
  - time
  - joker
```



> IF YOU WANNA CREATE IT !

**Create Plugin**

- Implementation interface.

the parser will give the arguments to your plugin if it matched.

```java
public interface IPlugin {
    
    void handle(String[] args);
    
    @Deprecated
    String command();
}
```



- Define command

We use the  annotation `@Plugin` to define the plugin meta data.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Plugin {

    // the plugin name
    String name() default "";

    // how to call the plugin normally
    String call();

    // fast commands
    String[] fastCommand() default {};
}
```



- Configure the condition

We won't create the plugin instance when it does not be called, so we should set the conditional configuration.

```java
@Bean
@ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "player")
public AudioPlayer audioPlayer(){
    return new AudioPlayer();
}
```

**Example**

the example of how to create the plugin `joker`.

- create `joker`

```java
@Plugin(name = "joker", call = "joker", fastCommand = {"j", "jk"})
public class Joker implements IPlugin {
    ...
}
```

- set bean condition

```java
@Bean
@ConditionalOnSummoned(name = "k-bot.plugins", havingValue = "joker")
public Joker joker() {
    return new Joker();
}
```

it is so easy to add an new plugin to `K-bot`, isn't it ! :tada:

---

## :wrench:Plugins

>  NOTE:  **Command** doesn't contain the command/name calling the bot. :pencil:

| Plugin  | Description                                                  | Command            | Example         |
| ------- | ------------------------------------------------------------ | ------------------ | --------------- |
| Player  | A audio player based on [lavaplayer](https://github.com/sedmelluq/lavaplayer). | play [- u ]        | play hello      |
| Joke    | Send a joke randomly.                                        | joke               | joke            |
| time    | Report the current time of the city.                         | time [cityname]    | time Tokyo      |
| weather | Report the current weather details of the city.              | weather [cityname] | weather Beijing |
|         |                                                              |                    |                 |
| ...     | waiting for your ideas !:rocket:                             |                    |                 |

---

## 📃 Statement

The API of bot are all getting from the open resources, if there has any problem, leaving an issues please.

[MIT](LICENSE) @Koy