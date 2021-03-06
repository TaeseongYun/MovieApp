## Rxjava

- BehaviorSubject -> 다른 깃허브 코드를 참고하여 만들었지만 대략적으로 어떻게 동작하는지 알게되었음. BehaviorSubject<T> 형의 Subject가 onNext를 하다가 subscribe 즉, 구독을 하게 되면 가장 마지막에 onNext했던 데이터가 발행된다.


- subscribeOn? observeOn? -> 아직까직은 자세히 어떻게 동작하는지 잘 모름. 후에 공부하고나서 변경예정. 대략적으로 subscribeOn은 메인 Observable 함수가 돌아가는 스레드를 지정 observeOn은 부가적인 데이터가 돌아가는 스레드 지정으로 알고있음.

- `흐름제어함수`는 데이터를 발행하는 속도와 데이터를 받아서 처리하는 속도가 차이가 발생할 때 사용하는 함수. (주로 UI 클릭이벤트에 사용된다는글을 본적 있음) 이번에는 throttleLast를 사용하였음. 매개변수로 50L TimeUnit.Millisecond를 넣었다. 즉 0.5초 동안 마지막에 들어온 데이터를 발행한다는 뜻.

## LiveData

- behaviorSubject를 이용하면서 livedata는 백그라운드 스레드에서 동작하지 못하는다는 점을 알게되었다. Room과는 정반대 Room은 메인 스레드에서 동작하지 못함.
  알고보니 LiveData도 백그라운드에서 동작이 가능하다. 다만 postValue 만 사용 가능 setValue (.value)는 반드시 메인 스레드에서 작동시켜야함.

## object 키워드와 companion object 키워드 차이

object와 companion object 의 차이점은 object 는 extend나 implement가 불가능하고 companion object는 가능하다.
object는 클래스 생성과 동시에 객체가 생성. companion object는 키워드 제시하면 static class가 생성 됌.

companion-object 네이밍 이용
![companion-object-name](img/companion-object-name-test.png)

![companion-object-name](img/companion-object-name-test2.png)

companion-object 네이밍 X
![companion-object-non-name](img/companion-object-no-name.png)

![companion-object-non-name2](img/companion-object-decode-java.png)

object decode 결과

![object-decode](img/object-keyword.png)


## Koin

koin 클래스 보게되면서 알게된점.

single 에는 특정 변수에 값을 지정하지 않으면 람다(vararg)로 매개변수가 여러개 올수 있는데 그중 내가 get()이라는 함수를 사용하면 해당 매개변수에 일치하는 클래스를 찾아서 반환해줌.

## Android ktx

Android ktx 란 kotlin으로 되어있는 확장 프로그램 세트이다. 쉽게 예를 들어 보겠다. 주로 이용하는 앱 데이터 캐싱하는 클래스 SharedPreferences를 예를 들어 보겠다.

로그인 세션을 저장시키려면 로그인 세션을 SharedPreferences에 저장하는데 이용하는데는 다음과 같이 구현한다.

sharedPreferences
            .edit()  // create an Editor
            .putBoolean("key", value)
            .apply() // write to disk asynchronously

이럴 때 사용하는게 ktx 이다 ktx를 사용하면 다음과 같이 사용할 수 있다.

// SharedPreferences.edit extension function signature from Android KTX - Core
    // inline fun SharedPreferences.edit(
    //         commit: Boolean = false,
    //         action: SharedPreferences.Editor.() -> Unit)

    // Commit a new value asynchronously
    sharedPreferences.edit { putBoolean("key", value) }

    // Commit a new value synchronously
    sharedPreferences.edit(commit = true) { putBoolean("key", value) }

## SaveStateHandle 클래스

안드로이드 어플리케이션을 사용하다보면 시스템에 의해서 앱이 죽는 경우가 있다. 그런 경우가 생기면 ViewModel 이라도 같은 데이터를 유지하지 못한다.

ex) count수, 입력한 문자 등..

자세히 알기 위해서 AndroidDeveloper 공식 홈페이지에 나와있는 SavedStateHandler 클래스 내용을 들고 왔다.

```
A handle to saved state passed down to ViewModel. You should use SavedStateViewModelFactory if you want to receive this object in ViewModel's constructor.

This is a key-value map that will let you write and retrieve objects to and from the saved state.

These values will persist after the process is killed by the system and remain available via the same object.

You can read a value from it via get(String) or observe it via LiveData returned by getLiveData(String).

You can write a value to it via set(String, Object) or setting a value to MutableLiveData returned by getLiveData(String).
```

쉽게 설명하면 다음과 같다.

- saved state는 ViewModel 로 받을 수 있다. 만약 받길 원하면 ViewModel 생성자에서 제시 하여라.

- saved state 는 map? 형태로 key-value 로 되어 있다.

- 해당 값은 시스템에 의해 종료된 이후에도 같은 오브젝트를 사용할 수 있다.

- get(key: String) -> 값 읽기, getLiveData(key: String) -> MutableLiveData 반환. LiveData를 통해서 사용가능.

- set(key: String, value: Any) -> 값 설정.


### 그럼 이제 어떻게 Koin(DI)과 같이 사용?

Koin 홈페이지에 가보면 이미 해당 SavedStateHandler에 대응하는 글이 나와 있었다.

ViewModel에서 SavedStateHanlder 를 사용하게 되면 inject 할 때 viewModel() 에서 stateViewModel()로 바꿔 줘야하고,
module로 설정하는 곳에선 viewModel{ (handler: SavedStateHandle) -> TestViewModel(handler) } 요런식으로 SavedStateHandle 클래스를 넘겨줘야한다.

그리고 stateViewModel()로 inject 해 줄때 bundle을 넘겨줄 수 있다. -> val myStateVM: MyStateVM by stateViewModel(bundle = myBundle)