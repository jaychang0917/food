import 'package:base/base.dart';

import 'root_event.dart';
import 'root_state.dart';

class RootBloc extends Bloc<RootEvent, RootState> {
  RootBloc({
    required sessionRepository,
  }) : _sessionRepository = sessionRepository, super(const RootState());

  final SessionRepository _sessionRepository;

  @override
  Stream<RootState> mapEventToState(RootEvent event) async* {
    if (event is Initialized) {
      yield* _mapInitializedToState(event);
    }
  }

  Stream<RootState> _mapInitializedToState(Initialized event) async* {
    try {
      final isLogged = await _sessionRepository.isLoggedIn();
      if (isLogged) {
        yield state.copyWith(navigation: HomeNavigation());
      } else {
        yield state.copyWith(navigation: LandingNavigation());
      }
    } on Exception catch (e) {
      yield state.copyWith(error: e);
    }
  }
}
