# SimpleBasketApp

The architecture of the application consists of 3 layers

- Domain layer : This layer contains only pure (java/kotlin) business logic does not contain any third party library.
- Data layer : This layer is used as data access layer. All data connected operations will be controlled in this layer.  [Repository Pattern](https://developer.android.com/jetpack/guide#recommended-app-arch)is used as structure
- Presenter layer : This layer is UI layer all UI connected operations will be handled in this layer

Libraries 
- Retrofit
- Glide
- Dagger Hilt
- Databinding
