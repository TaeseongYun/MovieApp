## Rxjava

- BehaviorSubject -> 다른 깃허브 코드를 참고하여 만들었지만 대략적으로 어떻게 동작하는지 알게되었음. BehaviorSubject<T> 형의 Subject가 onNext를 하다가 subscribe 즉, 구독을 하게 되면 가장 마지막에 onNext했던 데이터가 발행된다.


- subscribeOn? observeOn? -> 아직까직은 자세히 어떻게 동작하는지 잘 모름. 후에 공부하고나서 변경예정. 대략적으로 subscribeOn은 메인 Observable 함수가 돌아가는 스레드를 지정 observeOn은 부가적인 데이터가 돌아가는 스레드 지정으로 알고있음.

## LiveData

- behaviorSubject를 이용하면서 livedata는 백그라운드 스레드에서 동작하지 못하는다는 점을 알게되었다. Room과는 정반대 Room은 메인 스레드에서 동작하지 못함.
