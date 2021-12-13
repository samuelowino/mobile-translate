## Google Translate Automation App
> Manual translation sucks also why pay $$ for stuff you can automate.
> This project is based on;
* Google Cloud Platform Translate Api
* JAX-B XML Processing
* And general xml files processing.

## How to run the code
* Clone the project
```shell
$ git clone https://github.com/samuelowino/Google-Translate-Script

```

* Build the project

```shell
$ cd Google-Translate-Script
$ mvn clean install
```

* Import your Google Cloud Credentials

Before proceeding, it is recommended that all Google Cloud developers first read the Authentication overview topic to understand how authentication works in Google Cloud, including common scenarios and strategies. Additionally, before deploying an application to a production environment, ensure that you've read Authenticating as a service account.

**1. Setting the environment variable**

To use service accounts with the Cloud SDK, you need to set an environment variable where your code runs.

Provide authentication credentials to your application code by setting the environment variable GOOGLE_APPLICATION_CREDENTIALS. This variable applies only to your current shell session.
If you want the variable to apply to future shell sessions, 
set the variable in your shell startup file, for example in the ~/.bashrc or ~/.profile file.

> **Linux and macOS**

```shell
$ export GOOGLE_APPLICATION_CREDENTIALS="KEY_PATH"
```
- Replace KEY_PATH with the path of the JSON file that contains your service account key.

```shell
$ export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"
```

> **Windows**

** Powershell **

```shell
$ env:GOOGLE_APPLICATION_CREDENTIALS="KEY_PATH"
```
> **Command Prompt**

```shell
$ set GOOGLE_APPLICATION_CREDENTIALS=KEY_PATH
```
Setting the environment variable allows you to provide credentials separately from your application, without making changes to application code when you deploy. Alternately, you can explicitly specify the path to the service account key file in your code.
That might involve modifying the code to match your use case.

To obtain an API KEY got to

```shell
https://console.cloud.google.com/apis/credentials?project=[your-google-cloud-project]
```
- Pick one of the available service accounts keys or create a new one.
- In that case while selecting key choose JSON so that you can down the JSON file used above.

## Run Translation

- Add your strings to the output.xml file.
- Update <string> tags to <strings> 
- Execute the script while you go fetch some coffee, it takes some time.
```shell
$ cd target
$ java -jar Google-Translate-Labs-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

The project will generate localised content and place them in the project/target directory, in the format of [locale].xml e.g de.xml or zh.xml.
