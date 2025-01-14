:heartpulse:GsonAdapter:heartpulse:
Gson 数据类型匹配异常处理

[![Jitpack](https://jitpack.io/v/peihua8858/GsonAdapter.svg)](https://github.com/peihua8858)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/peihua8858)
[![Star](https://img.shields.io/github/stars/peihua8858/GsonAdapter.svg)](https://github.com/peihua8858/GsonAdapter)


## 目录
-[最新版本](https://github.com/peihua8858/GsonAdapter/releases/tag/1.0.6)<br>
-[如何引用](#如何引用)<br>
-[进阶使用](#进阶使用)<br>
-[如何提Issues](https://github.com/peihua8858/GsonAdapter/wiki/%E5%A6%82%E4%BD%95%E6%8F%90Issues%3F)<br>
-[License](#License)<br>




## 如何引用
* 把 `maven { url 'https://jitpack.io' }` 加入到 repositories 中
* 添加如下依赖，末尾的「latestVersion」指的是徽章[![Download](https://jitpack.io/v/peihua8858/GsonAdapter.svg)](https://jitpack.io/#peihua8858/GsonAdapter)里的版本名称，请自行替换。
```py
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

```

* 添加依赖

```py
dependencies {
     implementation 'com.github.peihua8858:GsonAdapter:${latestVersion}'
}
```
## 进阶使用
```kotlin
data class CountryBean(val country_code:String,val country_name:String)

val result="[{'country_code':'US','country_name':'United States'}]"
 val list = GsonFactory.create().fromJson<MutableList<CountryBean>>(
                    result,
                    object : TypeToken<MutableList<CountryBean>>() {}.type
                )
```
或
```kotlin
data class CountryBean(val country_code:String,val country_name:String)

val result="[{'country_code':'US','country_name':'United States'}]"
 val list = GsonUtils.fromJson<MutableList<CountryBean>>(
                    result,
                    object : TypeToken<MutableList<CountryBean>>() {}.type
                )
```
## License
```sh
Copyright 2023 peihua

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
