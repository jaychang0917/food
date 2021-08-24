import 'package:api/api.dart';
import 'session_manager.dart';

class SessionRepository {
  SessionRepository({
    required sessionApi,
    required sessionManager
  }) : _sessionApi = sessionApi,
       _sessionManager = sessionManager;

  final SessionApi _sessionApi;

  final SessionManager _sessionManager;

  Future<SessionLoginResult> login(String phoneNumber, String password) async {
    final body = SessionRequestBody((b) {
      b.phoneNumber = phoneNumber;
      b.password = password;
    });
    try {
      final response = await _sessionApi.postSessions(sessionRequestBody: body);
      final session = response.data!;
      _sessionManager.saveSession(session);
      return SessionLoginResultSuccess(session);
    } on Exception catch (e) {
      final apiError = e.toApiError();
      if (apiError?.code == "user_invalid_password") {
        return SessionLoginResultInvalidPassword();
      }
      rethrow;
    }
  }

  Future<bool> isLoggedIn() => _sessionManager.isLoggedIn();
}

abstract class SessionLoginResult {}

class SessionLoginResultSuccess extends SessionLoginResult {
  SessionLoginResultSuccess(this.data);

  final Session data;
}

class SessionLoginResultInvalidPassword extends SessionLoginResult {}
