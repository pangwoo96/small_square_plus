-- USER
CREATE TABLE users (
                      user_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      created_at  TIMESTAMP NOT NULL,
                      updated_at  TIMESTAMP NOT NULL,
                      username    VARCHAR(50) NOT NULL UNIQUE,
                      password    VARCHAR(255) NOT NULL,
                      nickname    VARCHAR(50) NOT NULL UNIQUE,
                      email       VARCHAR(100) NOT NULL UNIQUE,
                      name        VARCHAR(100) NOT NULL,
                      is_active   TEXT NOT NULL CHECK (is_active IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',
                      role        TEXT NOT NULL CHECK (role IN ('USER', 'ADMIN')) DEFAULT 'USER'
);

-- POST
CREATE TABLE post (
                      post_id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      created_at      TIMESTAMP NOT NULL,
                      updated_at      TIMESTAMP NOT NULL,
                      title           VARCHAR(255) NOT NULL,
                      content         TEXT NOT NULL,
                      comment_count   BIGINT NOT NULL DEFAULT 0,
                      like_count      BIGINT NOT NULL DEFAULT 0,
                      dislike_count   BIGINT NOT NULL DEFAULT 0,
                      view_count      BIGINT NOT NULL DEFAULT 0,
                      post_status     TEXT NOT NULL CHECK (post_status IN ('ACTIVE', 'DELETED')) DEFAULT 'ACTIVE',
                      user_id         BIGINT NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE post_images (
                                           post_id     BIGINT NOT NULL,
                                           image_url   VARCHAR(512) NOT NULL,
                                           CONSTRAINT fk_post_images_post
                                               FOREIGN KEY (post_id) REFERENCES post(post_id)
                                                   ON DELETE CASCADE
);

-- COMMENT
CREATE TABLE comment (
                         comment_id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         created_at      TIMESTAMP NOT NULL,
                         updated_at      TIMESTAMP NOT NULL,
                         content         TEXT NOT NULL,
                         like_count      BIGINT NOT NULL DEFAULT 0,
                         dislike_count   BIGINT NOT NULL DEFAULT 0,
                         user_id         BIGINT NOT NULL,
                         post_id         BIGINT NOT NULL,
                         parent_id       BIGINT DEFAULT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(user_id),
                         FOREIGN KEY (post_id) REFERENCES post(post_id),
                         FOREIGN KEY (parent_id) REFERENCES comment(comment_id) ON DELETE CASCADE
);

-- REACTION
CREATE TABLE reaction (
                          reaction_id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          created_at      TIMESTAMP NOT NULL,
                          updated_at      TIMESTAMP NOT NULL,
                          target_id       BIGINT NOT NULL,
                          target_type     TEXT NOT NULL CHECK (target_type IN ('POST', 'COMMENT')),
                          reaction_type   TEXT NOT NULL CHECK (reaction_type IN ('LIKE', 'DISLIKE')),
                          user_id         BIGINT NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(user_id),
                          UNIQUE (user_id, target_id, target_type)
);

-- REPORT
CREATE TABLE report (
                        report_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        created_at      TIMESTAMP NOT NULL,
                        updated_at      TIMESTAMP NOT NULL,
                        reason          VARCHAR(255) NOT NULL,
                        target_id       BIGINT NOT NULL,
                        target_type     TEXT NOT NULL CHECK (target_type IN ('POST', 'COMMENT')),
                        user_id         BIGINT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- INDEXES

-- 기존 인덱스가 존재하면 삭제하고, 새로 생성하는 방식

-- 게시글 조회수 TOP3
CREATE INDEX idx_post_view_desc ON post(view_count DESC);

-- 최신 게시글 조회
CREATE INDEX idx_post_created_at_desc ON post(created_at DESC);

-- 댓글 좋아요 TOP3
CREATE INDEX idx_comment_like_desc ON comment(post_id, like_count DESC);

-- 댓글 최신순
CREATE INDEX idx_comment_created_at_desc ON comment(created_at DESC);