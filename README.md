<h1 align="center">Shopping List</h1>
<h4 align="center">
	Create and add products to shopping lists.
</h4>

## Summary
This app was built in an attempt to explore the `Model-View-Intent`(MVI) architecture. The UI was inspired by [Keep Notes](https://play.google.com/store/apps/details?id=com.google.android.keep) while its architecture by [Star Wars search](https://github.com/Ezike/StarWarsSearch). [Please check out](https://github.com/Ezike/StarWarsSearch/blob/master/process.md) the explanation it has on the whats and whys of MVI used in the project.

## Screenshots
| Empty List | Add Products | Shopping Lists |
|:-:|:-:|:-:|
| ![1](screenshots/empty_list.png?raw=true) | ![2](screenshots/products.png?raw=true) | ![3](screenshots/shopping_lists.png?raw=true) |

## Features
* Clean Architecture with MVI (Uni-directional data flow)
* Kotlin Coroutines with Flow
* Dagger Hilt
* Kotlin Gradle DSL

## Prerequisite
To build this project, you require:
- Android Studio Giraffe or higher
- Gradle 8.1.2

## Libraries
- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [FlowBinding](https://github.com/ReactiveCircus/FlowBinding)
- [Room](https://developer.android.com/training/data-storage/room)
- [Kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Dagger Hilt](https://dagger.dev/hilt)
- [Kotlin Gradle DSL](https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin)

## License
This project is released under the Apache License 2.0.
See [LICENSE](./LICENSE) for details.

```
Copyright 2020 Zizoh James Anto. All rights reserved.

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
