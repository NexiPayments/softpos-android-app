# Pizza Pay Android Application

This codebase contains an example of an Android app implementing Nexi SoftPOS SDK.

To run the app it's required to have the backend running.

After running the backend import this codebase into Android Studio and set up your app by updating
the config at `app/src/main/java/com/example/pizzapay/Config.kt`. After
that you can run your app.

## Main functionalities

The application manages a cart and a checkout form, after the checkout it is possible to pay the
total via the Nexi SoftPOS app.

The `app/src/main/java/com/example/pizzapay/NexiApp2App.kt` class manage the SDK initialization.

In the `app/src/main/java/com/example/pizzapay/ui/checkout/CheckoutFragment.kt` file you can
find the SDK initialization and the payment function.

In the `app/src/main/java/com/example/pizzapay/ui/redirect/PaymentResultActivity.kt` file you
can find the SDK initialization and the revert function.

