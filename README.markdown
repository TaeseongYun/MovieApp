## Rxjava

- BehaviorSubject -> 다른 깃허브 코드를 참고하여 만들었지만 대략적으로 어떻게 동작하는지 알게되었음. BehaviorSubject<T> 형의 Subject가 onNext를 하다가 subscribe 즉, 구독을 하게 되면 가장 마지막에 onNext했던 데이터가 발행된다.


- subscribeOn? observeOn? -> 아직까직은 자세히 어떻게 동작하는지 잘 모름. 후에 공부하고나서 변경예정. 대략적으로 subscribeOn은 메인 Observable 함수가 돌아가는 스레드를 지정 observeOn은 부가적인 데이터가 돌아가는 스레드 지정으로 알고있음.

- `흐름제어함수`는 데이터를 발행하는 속도와 데이터를 받아서 처리하는 속도가 차이가 발생할 때 사용하는 함수. (주로 UI 클릭이벤트에 사용된다는글을 본적 있음) 이번에는 throttleLast를 사용하였음. 매개변수로 50L TimeUnit.Millisecond를 넣었다. 즉 0.5초 동안 마지막에 들어온 데이터를 발행한다는 뜻.

## LiveData

- behaviorSubject를 이용하면서 livedata는 백그라운드 스레드에서 동작하지 못하는다는 점을 알게되었다. Room과는 정반대 Room은 메인 스레드에서 동작하지 못함.

